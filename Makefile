testAndOpen: runIntegrationTest openLatestTest

runIntegrationTest:
	./gradlew test -Dtest.single=IntegrationTest

openLatestTest:
	open plugin/build/test-outputs/`ls -t plugin/build/test-outputs | head -n 1`/report.html
