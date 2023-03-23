---
--- company_psc_not_prev_filed
---
SELECT *
    FROM (SELECT *
        FROM (SELECT incorporation_number
            FROM (SELECT distinct incorporation_number
                FROM corporate_body cb
                    WHERE (SELECT COUNT (1) FROM transaction t
                        WHERE t.corporate_body_id = cb.corporate_body_id
                            AND t.transaction_type_id IN ('7260','7262','7263','7261','7264','7265','9008','7305','7270')) = 0
                            AND corporate_body_type_id = ?
                            AND trading_status_type_id = 1
                            AND proof_type_id = 0
                            AND language_type_id = 1
                            AND cb.corporate_body_short_name NOT LIKE '%(cloned)')
                   WHERE ROWNUM <= 5)
                ORDER BY DBMS_RANDOM.VALUE)
    WHERE ROWNUM = 1
