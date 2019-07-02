Silver Bars Exchange Board
==============

Testing the application
--------------

The Silver Bars application written with [Java SE 8] can be built using maven.
A client app has been included and can be run with the following command from the project root.

    mvn exec:java -Dexec.mainClass=com.silverbars.application.SilverBarsClientApp

The following unit tests verify various use cases of the functional aspects of the application.

- LiveOrderBoardTest
- OrderTest

The unit tests can be run using the following command.

    mvn test


Design Considerations
--------------
1. Clients can not directly instantiate LiveOrderBoard. Instead the static factory method [OrderBoardFactory.getInstance] can be used to obtain an IOrderBoard instance, which clients can then work with to register, cancel and display orders. This allows us to easily vary the IOrderBoard implementation for future releases and facilitates instance control e.g. the OrderBoardFactory currently uses a singleton IOrderBoard instance.

2. The Order class has been made immutable to simplify reasoning about application state when used in a concurrent context. The merge functionality does not mutate the state of an existing order but rather returns a new IOrderBoard instance whose quantity is an aggregate of the input orders.

3. Orders have been made to implement the comparable interface to leverage the functionality of sorted collections in a way that is consistent with the requirements of the application - ranking Sell orders in ascending price order and Buy orders in descending order of price. Also, the implementation uses a compound comparator to allow interoperability between buy orders and sell orders from a collections sorting perspective. Otherwise, it will become necessary to first split a list of orders into 2 separate lists before sorting according to price. However, only orders of the same type can be merged. Attempting to merge a buy and sell order will throw an IllegalArgumentException.   






