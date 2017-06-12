USE MATRIX
GO


DECLARE @accountID INT = 2
DECLARE @routeID INT = 2

SELECT *
FROM MTXCompanyGroup
WHERE accountID = @accountID

DECLARE @cnt INT = 1;

--2. Vehicle
SET @cnt = 1;
DECLARE @code VARCHAR(32)
DECLARE @vehicle INT
DECLARE @licensePlate varchar(32)

DECLARE db_cursor CURSOR FOR
SELECT gpsID
FROM MTXGps AS g
WHERE accountID = 2
AND gpsTypeID = 8
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
       SET @code = 'P'+ FORMAT(@cnt,'0000') --CAST(@cnt AS varchar)
	SET @licensePlate = 'DD-'+FORMAT(@cnt,'0000') --CAST(@cnt AS varchar)
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
				3,--@companyGroupID INT = NULL,
				1,--@isactive  INT  = NULL,
				null				

	SET @cnt = @cnt + 1;

       FETCH NEXT FROM db_cursor INTO @gpsID   
END   

CLOSE db_cursor   
DEALLOCATE db_cursor


SELECT count(*)
FROM MTXVehicle
WHERE accountID = 2
and routeID = 2