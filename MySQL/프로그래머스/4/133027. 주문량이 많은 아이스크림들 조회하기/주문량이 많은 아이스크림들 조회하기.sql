-- 코드를 입력하세요
SELECT FLAVOR
FROM (
    SELECT FLAVOR, SUM(TOTAL_ORDER) AS TOTAL
    FROM JULY
    GROUP BY (FLAVOR)
    UNION
    SELECT FLAVOR, SUM(TOTAL_ORDER) AS TOTAL
    FROM FIRST_HALF
    GROUP BY (FLAVOR)
) AS SUB
GROUP BY FLAVOR
ORDER BY SUM(TOTAL) DESC
LIMIT 3