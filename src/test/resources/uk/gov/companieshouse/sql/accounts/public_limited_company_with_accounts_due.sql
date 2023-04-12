---
--- Public Limited Company in England/Wales with Accounts due
---
SELECT incorporation_number
FROM corporate_body cb
             WHERE cb.incorporation_number not like 'c'
             AND cb.action_code_type_id = 0
             AND cb.trading_status_type_id = 1
             AND cb.corporate_body_type_id = 3
             AND cb.PROOF_TYPE_ID = 0
             AND cb.language_type_id = 1
             AND cb.register_location_type_id = 1
             AND cb.SUPER_SECURE_PSC_IND = 'N'
             AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'
             AND (SELECT COUNT (1) FROM sight_required_request sr WHERE sr.corporate_body_id = cb.corporate_body_id)=0
             AND (SELECT COUNT(1)
             FROM ACCOUNTS acc
             WHERE acc.corporate_body_id = cb.corporate_body_id
             AND accounts_made_up_date > add_months(SYSDATE, -12)
             AND accounts_made_up_date < SYSDATE)=0
             AND rownum <= 10
             ORDER BY dbms_random.value