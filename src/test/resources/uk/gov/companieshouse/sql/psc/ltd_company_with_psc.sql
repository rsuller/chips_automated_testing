--- private limited company with an individual psc
---
---
SELECT *
FROM corporate_body cb
             WHERE cb.incorporation_number not like 'c'
             AND cb.action_code_type_id = 0
             AND cb.trading_status_type_id = 1
             AND cb.corporate_body_type_id = 2
             AND cb.PROOF_TYPE_ID = 0
             AND cb.language_type_id = 1
             AND cb.register_location_type_id = 1
             AND cb.SUPER_SECURE_PSC_IND = 'N'
             AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'
             AND (SELECT COUNT (1) FROM sight_required_request sr WHERE sr.corporate_body_id = cb.corporate_body_id)=0
             AND (SELECT COUNT (1) FROM CORPORATE_BODY_APPOINTMENT cba
             WHERE CB.CORPORATE_BODY_ID = CBA.CORPORATE_BODY_ID
             AND CBA.APPOINTMENT_TYPE_ID = 5007
             AND cba.resignation_ind = 'N'
             AND cb.super_secure_psc_ind = 'N')=1
             AND rownum <= 10
             ORDER BY dbms_random.value