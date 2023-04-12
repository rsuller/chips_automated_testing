select
  distinct cs.confirm_stmt_made_up_date,
  cb.corporate_body_id,
  cb.incorporation_number
from
  corporate_body cb
  inner join confirmation_statement cs on cs.corporate_body_id = cb.corporate_body_id
  and cs.confirm_stmt_made_up_date <= sysdate - 365
  and cb.trading_status_type_id = 1
  AND cb.corporate_body_type_id = 2
  AND cb.PROOF_TYPE_ID = 0
  AND cb.language_type_id = 1
  AND cb.register_location_type_id = 1
  AND cb.SUPER_SECURE_PSC_IND = 'N'
  AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'
group by
  confirm_stmt_made_up_date,
  cb.corporate_body_id,
  cb.incorporation_number
order by
  dbms_random.value