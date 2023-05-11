---
--- private limited company ro in engwales id with an MVL action code

--- Companies with any MVL ceased case will be ignored, as we want to make sure the automated test is not going to pick up
--- any ceased case, as this will not be part of the sunny day scenario.

--- Companies with a case without a parent_transaction_id should be ignored
--- because there is an existing legacy bug in Chips which does not link the new transaction to the case

--- private limited company with an individual psc
---
---
SELECT *
FROM corporate_body cb
             WHERE cb.incorporation_number not like 'c'
             AND cb.action_code_type_id = 7001
             AND cb.trading_status_type_id = 1
             AND cb.corporate_body_type_id = 2
             AND cb.PROOF_TYPE_ID = 0
             AND cb.language_type_id = 1
             AND cb.register_location_type_id = 1
             AND cb.SUPER_SECURE_PSC_IND = 'N'
             AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'
             AND (SELECT COUNT (1) FROM sight_required_request sr WHERE sr.corporate_body_id = cb.corporate_body_id)=0
             AND (SELECT COUNT (1) FROM insolvency_case ic, insolvency_appointment ia
             WHERE CB.CORPORATE_BODY_ID = ic.CORPORATE_BODY_ID
             AND ic.insolvency_case_end_date BETWEEN SYSDATE AND TO_DATE('31/12/9999', 'dd/mm/yyyy')
             AND ic.next_expected_statement_date <= SYSDATE
             AND ia.insolvency_case_id = ic.insolvency_case_id
             AND ia.insolvency_app_end_date > SYSDATE) = 1
             AND rownum <= 20
             ORDER BY dbms_random.value