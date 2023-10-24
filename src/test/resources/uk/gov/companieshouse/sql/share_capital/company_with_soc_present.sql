---
--- Company with SOC present
---
SELECT *
    FROM (SELECT *
        FROM (SELECT *
            FROM (SELECT distinct incorporation_number
                FROM corporate_body cb
                    WHERE (SELECT COUNT (1) FROM transaction t,statement_of_capital soc, share_class_capital shcc
                        WHERE t.corporate_body_id = cb.corporate_body_id
                            AND T.TRANSACTION_ID = SOC.TRANSACTION_ID
                            AND soc.is_current_ind = 'Y'
                            AND cb.action_code_type_id = 0
                            AND cb.incorporation_number not like 'c'
                            AND cb.trading_status_type_id = 1
                            AND CB.LANGUAGE_TYPE_ID = 1
                            AND cb.proof_type_id = 0
                            AND cb.register_location_type_id = 1
                            AND cb.corporate_body_type_id = 2
                            AND cb.corporate_body_id not in (
                            SELECT corporate_body_id FROM sight_required_request
                            )
                            AND soc.corporate_body_id = cb.corporate_body_id
                            AND shcc.corporate_body_id = cb.corporate_body_id) = 1
                            AND cb.corporate_body_short_name NOT LIKE '%(cloned)')
                        WHERE ROWNUM <= 5)
                    ORDER BY DBMS_RANDOM.VALUE)
                WHERE ROWNUM = 1
