USE MATRIX
GO

SELECT *
FROM MTXAccount
-- 2	sanfelipe
SELECT *
FROM MTXRoute
WHERE accountID = 2
-- 2	RUTA 101

DECLARE @total BIGINT = 1000

DECLARE @accountID INT = 2
DECLARE @routeID INT = 2

--1. Gps
DECLARE @cnt INT = 1;
DECLARE @base BIGINT = 5000000000
DECLARE @esn BIGINT
WHILE @cnt <= @total
BEGIN
	SET @esn = @base+@cnt
	PRINT @esn
	INSERT INTO MTXGps(accountID,esn,gpsTypeID,isActive)
	VALUES(@accountID,@esn,8,1)

	SET @cnt = @cnt + 1;
END;

SELECT count(*)
FROM MTXGps AS g
WHERE accountID = 2
AND gpsTypeID = 8

SELECT *
FROM MTXGpsType

INSERT INTO MTXGpsType(name,supplierGpsID)
VALUES('SIMULADOR CALAMP',1);
