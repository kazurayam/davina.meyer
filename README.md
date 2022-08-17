# Proposal to @davina.meyer

at https://forum.katalon.com/t/advise-on-how-to-handle-timestamp-difference/68652

Try running the "Test Cases/TC1" to see how it performs.

TC1 converts a String of timestamp-like format into a java.time.LocalDateTime object.
TC1 also gets a current timestamp as a LocalDateTime object.
TC1 calculrates a difference between the two LocalDateTime objects.
TC1 verifies if the difference is less than 10 seconds. 
