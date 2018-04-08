# HaysRyanair
This is a maven project to meet the following requirements:
  - Automate a booking up to a declined payment on 'https://www.ryanair.com/ie/en'.
  - Use any card number and verify the error message.
  - The language used is Java(maven + junit)
  - Use Page Object Pattern
  - Show reporting for your results
  
## Configuration / Assumptions
I have configured an account named Testing Tester with 2 family members (wife + child) to be used in the execution.
A credit card has also been added, with number 4242 4242 4242 4242 (visa testing valid card but not usable in production environments)

## Assumptions
I am configuring a flight from Madrid to Brussels on 14/05/2018 for 2 adults and 1 child, I´m assuming that there will be a selectable flight for that date and that it will have at least 3 empty seats for the test. (This can be assumed as usually this tests won't be performed in a production environment but a testing one, configured for each case.)

### Prerequisites

Java, Maven

### Running

To run the tests, you just have to clone the repository and in the main folder execute:

```
mvn test
```

### Reporting

Each execution will produce an output html file, it can be found in: 
```
/reports/yyyy_MM_dd_hh_mm_ss.html
```

## Author

* **Francisco Javier Pérez**
