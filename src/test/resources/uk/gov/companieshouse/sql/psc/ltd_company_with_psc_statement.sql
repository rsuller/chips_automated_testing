select * from(
    select * from corporate_body cb
    join psc_statement ps on cb.corporate_body_id = ps.corporate_body_id
    where ps.psc_statement_type_id = 1
    and corporate_body_type_id = 1
    and action_code_type_id = 0
    and ps.psc_statement_end_date like '31-DEC-99'
    and rownum <=5
    order by DBMS_RANDOM.VALUE)
where rownum = 1;