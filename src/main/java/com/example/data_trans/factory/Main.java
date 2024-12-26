package com.example.data_trans.factory;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Create data for Module1: List<List<Integer>>
        List<List<Integer>> data = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6)
        );

        // Create instances of Module1, Module2, Module3, and Module4 with sample data
        Module1 module1 = new Module1(data);  // Module1 requires List<List<Integer>>

        // Module2 requires a HashMap<String, Integer>
        Module2 module2 = new Module2(new HashMap<>() {{
            put("a", 1);
            put("b", 2);
            put("c", 3);
            put("d", 4);
            put("e", 5);
        }});

        // Module3 requires a HashMap<String, String>
        HashMap<String, String> map3 = new HashMap<>();
        map3.put("key1", "value1");
        map3.put("key2", "value2");
        map3.put("key3", "value3");
        map3.put("key4", "value4");
        map3.put("key5", "value5");
        Module3 module3 = new Module3(map3);

        // Module4 requires a Set<String>
        Set<String> stringSet = new HashSet<>(Arrays.asList("apple", "banana", "cherry", "date", "elderberry"));
        Module4 module4 = new Module4(stringSet);

        Queue<String> stringQueue = new PriorityQueue<>();
        stringQueue.add("aa");
        stringQueue.add("bb");
        stringQueue.add("cc");
        stringQueue.add("dd");
        stringQueue.add("ee");
        Module5 module5 =  new Module5(stringQueue);

        Stack<String> stringStack = new Stack<>();
        stringStack.add("AA");
        stringStack.add("BB");
        stringStack.add("CC");
        stringStack.add("DD");
        stringStack.add("EE");
        Module6 module6 = new Module6(stringStack);

        // Create the CollectionModule that combines the data from all modules
        CollectionModule collectionModule = new CollectionModule(module1, module2, module3, module4, module5, module6);

        // Get the collected data
        HashMap<String, Object> collectedData = collectionModule.collect();

        // Print out combined data
        System.out.println("Combined Data: " + collectedData);
    }

    // If needed, you can also extract the collection logic into a separate static method:
    public static HashMap<String, Object> getCollectedData() {

        Scanner scanner = new Scanner(System.in);
        // Creating module instances...
        System.out.println("List<List<Integer>>에 들어갈 숫자 6개 입력");
        List<List<Integer>> data = Arrays.asList(
                Arrays.asList(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()),
                Arrays.asList(scanner.nextInt(), scanner.nextInt(), scanner.nextInt())
        );

        Module1 module1 = new Module1(data);
        Module2 module2 = new Module2(new HashMap<>() {{
            System.out.println("map에 들어갈 key,value 3개씩 입력");
            // 첫 번째 입력
            System.out.print("key1: ");
            String key1 = scanner.nextLine(); // key1 입력
            System.out.print("value1: ");
            while (!scanner.hasNextInt()) {
                System.out.println("정수를 입력해주세요.");
                scanner.next(); // 잘못된 입력 처리
            }
            int value1 = scanner.nextInt(); // value1 입력
            put(key1, value1);
            scanner.nextLine(); // 버퍼 비우기

            // 두 번째 입력
            System.out.print("key2: ");
            String key2 = scanner.nextLine(); // key2 입력
            System.out.print("value2: ");
            while (!scanner.hasNextInt()) {
                System.out.println("정수를 입력해주세요.");
                scanner.next(); // 잘못된 입력 처리
            }
            int value2 = scanner.nextInt(); // value2 입력
            put(key2, value2);
            scanner.nextLine(); // 버퍼 비우기

            // 세 번째 입력
            System.out.print("key3: ");
            String key3 = scanner.nextLine(); // key3 입력
            System.out.print("value3: ");
            while (!scanner.hasNextInt()) {
                System.out.println("정수를 입력해주세요.");
                scanner.next(); // 잘못된 입력 처리
            }
            int value3 = scanner.nextInt(); // value3 입력
            put(key3, value3);
        }});

        HashMap<String, String> map3 = new HashMap<>();
        System.out.println("map에 들어갈 key,value 3개씩 입력");
        map3.put(scanner.nextLine(), scanner.nextLine());
        map3.put(scanner.nextLine(), scanner.nextLine());
        map3.put(scanner.nextLine(), scanner.nextLine());
        Module3 module3 = new Module3(map3);

        System.out.println("set에 들어갈 3개 입력");
        Set<String> stringSet = new HashSet<>(Arrays.asList(scanner.nextLine(),scanner.nextLine(),scanner.nextLine()));
        Module4 module4 = new Module4(stringSet);

        System.out.println("큐에 들어갈 5개 입력");
        Queue<String> stringQueue = new PriorityQueue<>();
        stringQueue.add(scanner.nextLine());
        stringQueue.add(scanner.nextLine());
        stringQueue.add(scanner.nextLine());
        stringQueue.add(scanner.nextLine());
        stringQueue.add(scanner.nextLine());
        Module5 module5 = new Module5(stringQueue);

        System.out.println("스택에 들어갈 5개 입력");
        Stack<String> stringStack = new Stack<>();
        stringStack.add(scanner.nextLine());
        stringStack.add(scanner.nextLine());
        stringStack.add(scanner.nextLine());
        stringStack.add(scanner.nextLine());
        stringStack.add(scanner.nextLine());
        Module6 module6 = new Module6(stringStack);

        CollectionModule collectionModule = new CollectionModule(module1, module2, module3, module4, module5, module6);
        return collectionModule.collect();
    }


}
