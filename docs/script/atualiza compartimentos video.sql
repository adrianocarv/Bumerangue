
delete from bmg_elemento_dominio where id_dominio =  '4028808b14ebc5110114ebc552450001';
insert into bmg_elemento_dominio select * from bmg_elemento_dominio_copy where id_dominio =  '4028808b14ebc5110114ebc552450001';

delete from bmg_elemento_dominio where id_dominio =  '4028808b1464049301146404a6cf0001';
delete from bmg_dominio where id = '4028808b1464049301146404a6cf0001';
insert into bmg_dominio select * from bmg_dominio_copy where id =  '4028808b1464049301146404a6cf0001';
insert into bmg_elemento_dominio select * from bmg_elemento_dominio_copy where id_dominio =  '4028808b1464049301146404a6cf0001';


