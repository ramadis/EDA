all:
	$(clean)
	javac flow/*.java
	java flow.InputParser ./maze exact


clean:
	rm flow/*.class
