# Functional programming
There are tasks for using functional approach of programming.

## Exercises

### Exercise 1

There are tweets with some hashtags. 
You should collect hashtags and sort them by count in the text from the largest to the smallest.
```
        List<String> tweets = List.of(
                "#Java and #Scala play a key role in AI and cognitive computing. IBM believes the future lies in cognitive technology.",
                "Here's an update on IBM’s backing of #Scala and Lightbend: http8/ibm-lightbend-partnership-enterprise",
                "IBM teams up with @lightbend to create a unified platform for #Java and #Scala #cognitive application development with tag #ChallengeEveryDay."
        );
```

There are methods that should be refactored. 
They should be improved. 
Methods should be rewritten with a functional approach using Optional and Stream API.
All necessary classes, models, methods and constants should be created according to each task.


### Exercise 2

```
    public String getId(Transaction transaction) {
        String result = null;
        Item item = transaction.getItem();
        if (item != null) {
            Element element = item.getElement();
            if (element != null && element.getId() != null) {
                result = Optional.of(customerCommonData.getId().toString())
                        .orElse(null);
            }
        }
        return result;
    }
```

### Exercise 3

```
    public List<Item> filter(List<Item> items) {
        return items.stream()
                .filter(item -> !SOME_WRONG_TYPE.contentEquals(item.getType()))
                .filter(item -> {
                    CharSequence elementId = getElementId(item.getTransactionId());
                    if (Objects.nonNull(elementId)) {
                        long id = Long.parseLong(elementId.toString());
                        return id <= 7 && id >= 2;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
```

### Exercise 4

```
    public List<Item> convert(Transaction transaction) {
        List<Item> items = new ArrayList<>();
        for (Item item : getItems(transaction)) {
            Optional.ofNullable(item.getElement())
                    .flatMap(ElementMapper::map)
                    .ifPresent(element -> {
                        Optional.ofNullable(item.getStartDateTime())
                                .ifPresent(startDateTime -> {
                                    element.setStartDateTime(DateTimeUtils.getInstant(startDateTime));
                                });
                        items.add(item);
                    });
        }
        return items;
    }
```

### Exercise 5

```
    public List<Item> convert(Transaction transaction) {
        List<Item> items = new ArrayList<>();
        List<Item> filteredItems = filterItems(transaction);
        for (Item filteredItem : filteredItems) {
            Optional.ofNullable(filteredItem.getSale())
                    .ifPresent(sale -> {
                        if (!filterSale(sale)) {
                            Item newItem = createItem(filteredItem);
                            items.add(newItem);
                        }
                    });

            Optional.ofNullable(filteredItem.getReturn())
                    .ifPresent(returnItem -> {
                        if (isReturnTransaction(transaction)) {
                            Optional.ofNullable(filteredItem.getData())
                                    .ifPresent(data -> Optional.ofNullable(data.getType())
                                            .ifPresent(type -> {
                                                if (type.toString().equals(SOME_TYPE)) {
                                                    Item newItem = createItem(filteredItem);
                                                    items.add(newItem);
                                                }
                                            }));
                        }
                    });

            Optional.ofNullable(filteredItem.getFuelSale())
                    .ifPresent(fuelSale -> {
                        Item newItem = createItem(filteredItem);
                        items.add(newItem);
                    });
        }
        return items;
    }
```

### Exercise 6

```
    public boolean filter(Item item) {
        CharSequence type = item.getType();
        Boolean exist = item.isExist();

        if (type != null && type.toString().equalsIgnoreCase(SOME_TYPE)) {
            return true;
        } else {
            return exist != null && exist;
        }
    }
```

### Exercise 7

```
   public List<Item> filter(List<Item> items) {
        List<Item> filteredItems = new ArrayList<>();
        Set<Long> parentItemIds = new HashSet<>();
        for (Item item : items) {
            Optional.ofNullable(item.isCancelled())
                    .ifPresent(flag -> {
                        if (Boolean.TRUE.equals(flag)) {
                            filteredItems.add(item);
                            if (item.getParentId() != null) {
                                parentItemIds.add(item.getParentId());
                            }
                        }
                    });
            Optional.ofNullable(item.getAction())
                    .ifPresent(action -> {
                        if (action.toString().equalsIgnoreCase(SKIPPED)) {
                            filteredItems.add(item);
                        }
                    });
            Optional.ofNullable(item.getReturnReason())
                    .ifPresent(returnReason -> filteredItems.add(item));
        }
        for (Item item : items) {
            if (parentItemIds.contains(item.getId())) {
                filteredItems.add(item);
            }
        }
        List<Item> result = new ArrayList<>(items);
        result.removeAll(filteredItems);
        return result;
    }
```
