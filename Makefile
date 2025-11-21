# Makefile pour compiler et lancer les tests du projet POO

# Options de compilation
JAVAC = javac
JAVA = java
CLASSPATH = bin:lib/gui.jar
SRC_DIR = src
BIN_DIR = bin

# Cibles principales
all: compile

# Compilation de tous les fichiers
compile:
	$(JAVAC) -d $(BIN_DIR) -classpath lib/gui.jar $(SRC_DIR)/**/*.java

# Tests des automates cellulaires
testConway: compile
	$(JAVA) -classpath $(CLASSPATH) conway.TestConwaySimulator

testImmigration: compile
	$(JAVA) -classpath $(CLASSPATH) immigration.TestImmigrationSimulator

testImmigrationBig: compile
	$(JAVA) -classpath $(CLASSPATH) immigration.TestImmigrationBig

testSchelling: compile
	$(JAVA) -classpath $(CLASSPATH) schelling.TestSchellingSimulator

# Tests des boids
testBoidSimple: compile
	$(JAVA) -classpath $(CLASSPATH) boids.BoidTest

testBoidMultiGroup: compile
	$(JAVA) -classpath $(CLASSPATH) boids.BoidMultiGroupTest

testBoidPreyPredator: compile
	$(JAVA) -classpath $(CLASSPATH) boids.BoidPreyPredatorTest

# Test des balles
testBalls: compile
	$(JAVA) -classpath $(CLASSPATH) Ball.TestBallsSimulator

testBallsUpdated: compile
	$(JAVA) -classpath $(CLASSPATH) Ball.TestBalls

# Lancer tous les tests (avec pause entre chacun)
testAll: testConway testImmigration testImmigrationBig testSchelling testBoidSimple testBoidMultiGroup testBoidPreyPredator testBalls

# Nettoyage
clean:
	rm -rf $(BIN_DIR)/

# Help
help:
	@echo "Cibles disponibles:"
	@echo "  make compile              - Compile tous les fichiers"
	@echo "  make testConway           - Lance le test Conway"
	@echo "  make testImmigration      - Lance le test Immigration (petit)"
	@echo "  make testImmigrationBig   - Lance le test Immigration (80x80, aléatoire)"
	@echo "  make testSchelling        - Lance le test Schelling"
	@echo "  make testBoidSimple       - Lance le test Boid simple"
	@echo "  make testBoidMultiGroup   - Lance le test Boid multi-groupe"
	@echo "  make testBoidPreyPredator - Lance le test Boid proie/prédateur"
	@echo "  make testBalls            - Lance le test Balles"
	@echo "  make testAll              - Lance tous les tests"
	@echo "  make clean                - Supprime les fichiers compilés"
	@echo "  make help                 - Affiche cette aide"

.PHONY: all compile testConway testImmigration testImmigrationBig testSchelling testBoidSimple testBoidMultiGroup testBoidPreyPredator testBalls testAll clean help
