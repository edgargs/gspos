SELECT *
FROM MTXEvent
WHERE accountID = 2
ORDER BY 1 DESC

SELECT *
FROM MTXRouteTerminal
WHERE accountID = 2

SELECT *
FROM MTXLogTable
ORDER BY 1 DESC

SELECT count(*)
FROM MTXDispatch
-- UPDATE MTXDispatch SET dispatchStatusID = 5
where accountID = 2
and routeID = 2
and dispatchStatusID = 2
