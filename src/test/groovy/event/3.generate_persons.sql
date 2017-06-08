USE MATRIX
GO

DECLARE @total BIGINT = 100

DECLARE @accountID INT = 5
DECLARE @routeID INT = 4


DECLARE @cnt INT = 1;

--3. Person
SET @cnt = 1;
DECLARE @base BIGINT = 50000000
DECLARE @uniqueID BIGINT
WHILE @cnt <= @total
BEGIN
	SET @uniqueID = @base+@cnt
	PRINT @uniqueID
	INSERT INTO MTXPerson(accountID,routeID,name,lastName,uniqueID,personTypeID,isActive)
	VALUES(@accountID,@routeID,'N'+CAST(@cnt AS varchar),'L'+CAST(@cnt AS varchar),@uniqueID,1,1)

	SET @cnt = @cnt + 1;
END;

SELECT *
FROM MTXPerson
WHERE accountID = 5
and routeID = 4