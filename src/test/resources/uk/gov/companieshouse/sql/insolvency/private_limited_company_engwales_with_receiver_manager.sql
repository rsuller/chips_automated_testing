---
--- private limited company ro in engwales id with a RECEIVER MANAGER action code
--- Companies with a ceased insolvency case are excluded to ensure the automated test 
--- does not pick up an already ceased case (not included in the sunny day scenario).

SELECT * 
FROM (  SELECT * 
        FROM (  SELECT incorporation_number 
                FROM (  SELECT distinct incorporation_number 
                        FROM corporate_body cb, insolvency_case ic, insolvency_appointment ia
                        WHERE cb.corporate_body_id = ic.corporate_body_id
                            AND ia.insolvency_case_id = ic.insolvency_case_id
                            AND insolvency_app_end_date > SYSDATE
                            AND trading_status_type_id = 1
                            AND proof_type_id = 0
                            AND language_type_id = 1
                            AND register_location_type_id = 1
                            AND action_code_type_id = 7301
                            AND corporate_body_type_id = 2
                            AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'
                            AND (SELECT COUNT (1) FROM sight_required_request sr WHERE sr.corporate_body_id = cb.corporate_body_id)=0
                            AND next_expected_statement_date <= SYSDATE
                            AND (next_expected_statement_date - insolvency_case_eff_date) < 365
                            AND insolvency_case_end_date = TO_DATE('31/12/9999', 'dd/mm/yyyy')
                            AND NOT EXISTS (    SELECT insolvency_case_id 
                                                FROM insolvency_case ic
                                                WHERE insolvency_case_end_date <= SYSDATE
                                                    AND ic.corporate_body_id = cb.corporate_body_id
                                                    AND insolvency_case_type_id = 5))
                WHERE ROWNUM <= 100)
        ORDER BY DBMS_RANDOM.VALUE)
WHERE ROWNUM = 1
