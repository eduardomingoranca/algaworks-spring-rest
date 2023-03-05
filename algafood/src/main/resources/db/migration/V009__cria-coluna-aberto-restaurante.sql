alter table algafood.restaurante add aberto TINYINT(1) not null;
update algafood.restaurante set aberto = true;
