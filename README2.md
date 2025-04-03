# Functional programming
There are tasks to learn covering with unit tests.

## Exercises

All methods need to be covered with unit tests. Jupiter (JUnit5) and mockito libraries can be used.
All logic branches need to be covered. 
If some methods are not implemented it should be created as protected methods with throwing exception inside. These methods need to be mocked in tests.

### Exercise 1

```java
@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandler {

    private final DLQHandler dlqHandler;
    private final Metrics metrics;

    public <I, O> Try<O> handle(Function<I, O> func, I input) {
        Function<ApplicationException, Try<O>> handleFunction = ex -> {
            log.error("An error occurred while processing the message", ex);
            dlqHandler.send(input, ex);
            metrics.incrementMessagesInDlqCounter();
            return Try.success(null);
        };

        return Try.of(() -> func.apply(input))
                .recoverWith(MappingException.class, handleFunction::apply)
                .recoverWith(Exception.class, ex -> handleFunction.apply(new ApplicationException(ErrorCode.UNKNOWN, ex.getMessage(), ex)));
    }

}
```

### Exercise 2

```java
@Component
@RequiredArgsConstructor
public class TransactionConverter {

    private final TransactionMapper transactionMapper;

    public Transaction convert(Item item, Map<String, Object> headers) {
        return Try.of(() -> transactionMapper.map(item.getElement(), headers))
                .recoverWith(
                        BusinessException.class,
                        ex -> Try.failure(new BusinessException(
                                ex.getMessage(),
                                posLogRoot.getElement().getTransactionId().toString(),
                                ex.getCause()
                        ))
                )
                .get();

    }

}
```

Exersice 3 contains logic from MapStruct. this library needs to be added in project and implementation should be generated.
Generated implementation should be covered with unit tests.

### Exercise 3

```java
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemMapper {

    @Named("mapItems")
    List<Item> map(List<Element> elements);

    @Mapping(target = "typeId", constant = StringUtils.EMPTY)
    @Mapping(target = "description", source = "type")
    @Mapping(target = "change.amount", expression = "java(java.math.BigDecimal.valueOf(0.0))")
    @Mapping(target = "change.currencyCode", source = "amount.currencyCode")
    Item map(Element element);

}
```

### Exercise 4

```java
    public PriceModifier convert(PriceModifier priceModifier, MonetaryAmount monetaryAmount) {
        var newPriceModifier = map(priceModifier, monetaryAmount);
        populate(newPriceModifier, priceModifier);
        return newPriceModifier;
    }
```

### Exercise 5

```java
@Service("ItemService")
@RequiredArgsConstructor
public class ItemCacheService implements ItamService {

    private final GenericCache<String, ItemDocument> itemCache;
    private final SainService nonCacheSainService;

    @Override
    public ItemDocument get(String key) {
        return itemCache.of(nonCacheSainService::get)
                .apply(key);
    }

}
```

### Exercise 6

```java
@Component
public class MetricService {
    private static final String APP_TAG = "app";

    @Value("${spring.application.name}")
    private String appName;

    private Counter messagesReceivedCounter;
    private Counter messagesInDlqCounter;
    private Timer messageProcessingTimer;

    @PostConstruct
    public void init() {
        messagesReceivedCounter = Metrics.counter("total.messages", APP_TAG, appName);
        messagesInDlqCounter = Metrics.counter("dlq.messages", APP_TAG, appName);
        messageProcessingTimer = Metrics.timer("timer.process", APP_TAG, appName);
    }

    public void incrementMessagesReceivedCounter() {
        messagesReceivedCounter.increment();
    }

    public void incrementMessagesInDlqCounter() {
        messagesInDlqCounter.increment();
    }

    public void stopProcessingTimer(Timer.Sample processTimer) {
        processTimer.stop(messageProcessingTimer);
    }
}
```

### Exercise 7

```java
public class JsonSerializer<T> implements Serializer<T> {

    private ObjectMapper objectMapper;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        objectMapper = new ObjectMapper();
        JacksonConfig.initConfigs(objectMapper);
    }

    @Override
    public byte[] serialize(String topic, T data) {
        return Optional.ofNullable(data)
                .map(item ->
                        Try.of(() -> objectMapper.writeValueAsBytes(data))
                                .recoverWith(ex -> Try.failure(new SerializingException("Error serializing JSON message", ex)))
                                .get()
                )
                .orElse(null);
    }

}
```

