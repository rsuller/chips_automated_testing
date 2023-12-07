---
--- ltd company with active corporate director
---
SELECT *
    FROM (SELECT *
        FROM (SELECT incorporation_number
            FROM (SELECT distinct incorporation_number
                FROM corporate_body cb,corporate_body_appointment cba, officer
                    WHERE CB.CORPORATE_BODY_ID = CBA.CORPORATE_BODY_ID
                    AND CBA.OFFICER_ID = OFFICER.OFFICER_ID
                                 AND cb.trading_status_type_id = 1
                                 AND cb.corporate_body_type_id = 2
                                 AND CB.PROOF_TYPE_ID = 0
                                 AND action_code_type_id = 0
                                 AND cb.language_type_id = 1
                                 AND CBA.APPOINTMENT_TYPE_ID = 1
                                 AND cba.resignation_ind = 'N'
                                 AND corporate_officer_ind = 'Y'
                                 AND corporate_officer_verified_ind = 'Y'
                                 AND (SELECT COUNT (1) FROM sight_required_request sr WHERE sr.corporate_body_id = cb.corporate_body_id)=0
                                 AND cb.corporate_body_short_name NOT LIKE '%(cloned)%')
                   WHERE ROWNUM <= 100)
        ORDER BY DBMS_RANDOM.VALUE)
 WHERE ROWNUM = 1
