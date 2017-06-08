USE MATRIX
GO

DECLARE @total BIGINT = 100

DECLARE @accountID INT = 5
DECLARE @routeID INT = 4

--1. Gps
DECLARE @cnt INT = 1;
DECLARE @base BIGINT = 5000000000
DECLARE @esn BIGINT
WHILE @cnt <= @total
BEGIN
	SET @esn = @base+@cnt
	PRINT @esn
	INSERT INTO MTXGps(accountID,esn,gpsTypeID,isActive)
	VALUES(@accountID,@esn,3,1)

	SET @cnt = @cnt + 1;
END;

SELECT *
FROM MTXGps AS g
WHERE accountID = 5
