# Limpar projeto
clean:
	@mvn clean

# Compilar código
compile:
	@mvn clean compile

# Empacotar aplicação
install: compile
	@mvn package

# Executar aplicação (ajuste o nome do jar conforme necessário)
run: install
	@java -jar target/seu-artefato.jar

# Executar testes unitários
test-unit:
	@mvn test

# Executar testes de integração
test-integration:
	@mvn verify -Pintegration

# Executar testes de sistema
test-system: run
	@echo "Run System Tests (defina seu comando de sistema aqui)"

# Validar aplicação
check: test-unit test-integration
	@echo "Run Validation Tests"
