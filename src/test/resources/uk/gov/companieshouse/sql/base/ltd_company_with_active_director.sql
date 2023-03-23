---
--- ltd company with active director
---
SELECT *
  FROM (SELECT *
        FROM (SELECT incorporation_number
           FROM (SELECT distinct incorporation_number
                FROM corporate_body cb,corporate_body_appointment cba
                    WHERE CB.CORPORATE_BODY_ID = CBA.CORPORATE_BODY_ID
                                 AND cb.trading_status_type_id = 1
                                 AND cb.corporate_body_type_id = 2
                                 AND CB.LATE_FILING_PENALTY_IND = 'N'
                                 AND CB.PROOF_TYPE_ID = 0
                                 AND action_code_type_id = 0
                                 AND cb.language_type_id = 1
                                 AND CBA.APPOINTMENT_TYPE_ID = 2
                                 AND URA_SAME_AS_SERVICE_IND = 'N'
                                 AND SERVICE_SAME_AS_ROA_IND = 'N'
                                 AND cba.resignation_ind = 'N'
                                 AND cb.corporate_body_short_name NOT LIKE '%(cloned)%')
                   WHERE ROWNUM <= 20)
        ORDER BY DBMS_RANDOM.VALUE)
 WHERE ROWNUM = 1