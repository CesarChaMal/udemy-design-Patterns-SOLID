@echo off
echo === Creating Missing Tests for All Modules ===
echo.

set MODULES_NEEDING_TESTS=AntiCorruptionLayerPattern APIGatewayPattern ApplicationControllerPattern BFFPattern BulkheadPattern BusinessDelegatePattern BusinessObjectPattern CacheAsidePattern CompositeEntityPattern CompositeViewPattern ContextObjectPattern CQRSPattern DAOFactoryPattern DatabasePerServicePattern DependencyInjectionPattern DTOFactoryPattern EntityAggregatorPattern EventSourcingPattern EventStorePattern FastLaneReaderPattern FrontControllerPattern InterceptingFilterPattern LazyLoadingPattern PageControllerPattern PublisherSubscriberPattern RetryPattern SagaPattern ServiceActivatorPattern SessionFacadePattern SidecarPattern SpecificationPattern StranglerFigPattern TimeoutPattern TransferObjectAssemblerPattern UnitOfWorkPattern ValueListHandlerPattern ValueObjectPattern WebServiceBrokerPattern

for %%m in (%MODULES_NEEDING_TESTS%) do (
    echo Creating test for %%m...
    mkdir "%%m\src\test\java\com\balazsholczer\%%m" 2>nul
)

echo.
echo === Test Directory Creation Complete ===