---
--- Private Limited Company in Eng/Wales with No sight required or Super secure PSC indicator
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
             AND rownum <= 30
             ORDER BY dbms_random.value
