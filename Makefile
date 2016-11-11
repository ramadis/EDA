all:
	$(clean)
	javac flow/*.java
	java flow.InputParser ./maze exact progress

approx:
	java flow.InputParser ./maze approx 5000

clean:
	rm flow/*.class

jar:
	jar cmvf META-INF/MANIFEST.MF tpe.jar  flow/*.class

run:
	java -jar tpe.jar ./maze approx 3000

runexact:
	java -jar tpe.jar ./maze exact


run2:
	java -jar tpe.jar ./maze2 approx 5000 progress
