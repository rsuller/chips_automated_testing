---
--- private limited company with unspecified RO parameter
---
SELECT *
    FROM (SELECT *
        FROM (SELECT incorporation_number
            FROM (SELECT DISTINCT incorporation_number
                FROM corporate_body cb
                    WHERE (SELECT COUNT (1) FROM transaction t
                    WHERE t.corporate_body_id = cb.corporate_body_id
                                 AND t.transaction_type_id = 430) = 0
                                 AND trading_status_type_id = 1
                                 AND proof_type_id = 0
                                 AND language_type_id = 1
                                 AND register_location_type_id = ?
                                 AND action_code_type_id = 0
                                 AND corporate_body_type_id = 2
                                 AND cb.corporate_body_short_name NOT LIKE '%(cloned)'
                                 AND cb.incorporation_number not like 'c'
                                 AND cb.corporate_body_id not in (
                                    SELECT corporate_body_id FROM sight_required_request
                                 )
                                 AND NOT EXISTS (SELECT * from insolvency_case ic where cb.corporate_body_id = ic.corporate_body_id)
            )
        WHERE ROWNUM <= 5)
    ORDER BY DBMS_RANDOM.VALUE)
 WHERE ROWNUM = 1
