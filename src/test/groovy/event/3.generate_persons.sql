USE MATRIX
GO

DECLARE @total BIGINT = 1000

DECLARE @accountID INT = 2
DECLARE @routeID INT = 2


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
	VALUES(@accountID,@routeID,'N'+FORMAT(@cnt,'0000'),'L'+FORMAT(@cnt,'0000'),@uniqueID,1,1)

	SET @cnt = @cnt + 1;
END;

SELECT COUNT(*)
FROM MTXPerson
WHERE accountID = 2
and routeID = 2