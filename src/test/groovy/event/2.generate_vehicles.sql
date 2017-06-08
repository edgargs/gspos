USE MATRIX
GO

DECLARE @total BIGINT = 100

DECLARE @accountID INT = 5
DECLARE @routeID INT = 4


DECLARE @cnt INT = 1;

--2. Vehicle
SET @cnt = 1;
DECLARE @code VARCHAR(32)
DECLARE @vehicle INT
DECLARE @licensePlate varchar(32)

DECLARE db_cursor CURSOR FOR
SELECT gpsID
FROM MTXGps AS g
WHERE accountID = 5
AND vehicleID  IS NULL
/*AND NOT EXISTS (SELECT * FROM MTXVehicle AS v
				WHERE accountID = 5
				and routeID = 4
				and v.gpsID = g.gpsID)*/

DECLARE @gpsID INT

OPEN db_cursor   
FETCH NEXT FROM db_cursor INTO @gpsID   

WHILE @@FETCH_STATUS = 0   
BEGIN   
       SET @code = 'P'+CAST(@cnt AS varchar)
	SET @licensePlate = 'DDD-'+CAST(@cnt AS varchar)
	EXEC [dbo].[MTX_sp_VehicleNew]
				@vehicle,
				@accountID,
				@routeID,
				@code,
				@gpsID,
				@licensePlate,
				NULL,
				NULL,
				NULL,
				NULL,
				1,--@companyGroupID INT = NULL,
				1,--@isactive  INT  = NULL,
				null				

	SET @cnt = @cnt + 1;

       FETCH NEXT FROM db_cursor INTO @gpsID   
END   

CLOSE db_cursor   
DEALLOCATE db_cursor


SELECT *
FROM MTXVehicle
WHERE accountID = 5
and routeID = 4