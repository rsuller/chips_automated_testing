---
--- ltd company with active corporate secretary
---
select cb.*
from corporate_body_appointment cba
join corporate_body cb on cba.corporate_body_id = cb.corporate_body_id
join officer_detail od on cba.officer_id = od.officer_id
join officer o on od.officer_id = o.officer_id
where cb.trading_status_type_id = 1
AND cb.corporate_body_type_id = 2
AND CB.LATE_FILING_PENALTY_IND = 'N'
AND CB.PROOF_TYPE_ID = 0
AND cb.action_code_type_id = 0
AND cb.language_type_id = 1
AND CBA.APPOINTMENT_TYPE_ID = 1
AND O.CORPORATE_OFFICER_IND = 'Y'
AND cba.RESIGNATION_IND = 'N'
AND (SELECT COUNT (1) FROM sight_required_request sr WHERE sr.corporate_body_id = cb.corporate_body_id)=0
AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'
AND ROWNUM <= 100
ORDER BY DBMS_RANDOM.VALUE
