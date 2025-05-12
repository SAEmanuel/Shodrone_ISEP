all: main

main: main.o drone.o simulation.o parser.o tinyexpr.o
	gcc -Wall -o main main.o drone.o simulation.o parser.o tinyexpr.o -lm

main.o: main.c
	gcc -Wall -c main.c

drone.o: drone.c
	gcc -Wall -c drone.c

simulation.o: simulation.c
	gcc -Wall -c simulation.c

parser.o: parser.c
	gcc -Wall -c parser.c

tinyexpr.o: tinyexpr.c
	gcc -Wall -c tinyexpr.c

clean:
	rm -f *.o main
