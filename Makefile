all:
	$(clean)
	javac flow/*.java
	java flow.InputParser ./maze exact progress

approx:
	java flow.InputParser ./maze approx 5000

clean:
	rm flow/*.class
