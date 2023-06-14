---
--- private limited company id with mortgages
---
SELECT *
    FROM (SELECT incorporation_number, corporate_body_id
        FROM (SELECT incorporation_number, corporate_body_id
            FROM (SELECT cb.incorporation_number, corporate_body_id
                FROM corporate_body cb
                    WHERE cb.register_location_type_id = 1
                                 AND cb.trading_status_type_id = 1
                                 AND cb.proof_type_id = 0
                                 AND cb.language_type_id = 1
                                 AND cb.corporate_body_type_id = 2
                                 AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'
                                 AND (SELECT COUNT (1)
                                    FROM mortgage mo
                                        WHERE mo.corporate_body_id = cb.corporate_body_id
                                             AND mo.mortgage_satisfaction_ind = 'N') = 1)
                   WHERE ROWNUM <= 10)
        ORDER BY DBMS_RANDOM.VALUE)
 WHERE ROWNUM = 1