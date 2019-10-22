CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL,
  `stock` int(11) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `goods` VALUES ('1', '90000', '1');


