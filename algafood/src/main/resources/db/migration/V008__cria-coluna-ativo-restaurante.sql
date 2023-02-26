alter table algafood.restaurante add ativo TINYINT(1) not null;
update algafood.restaurante set ativo = true;
