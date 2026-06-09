CREATE DATABASE IF NOT EXISTS fridge_food_manage
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE fridge_food_manage;

DROP TABLE IF EXISTS food_item;
DROP TABLE IF EXISTS food_category;

CREATE TABLE food_category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  color VARCHAR(20) NOT NULL DEFAULT '#3f8cff',
  sort_order INT NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_food_category_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE food_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(80) NOT NULL,
  category_id BIGINT NOT NULL,
  quantity DECIMAL(10,2) NOT NULL DEFAULT 1,
  unit VARCHAR(20) NOT NULL DEFAULT '份',
  production_date DATE NULL,
  shelf_life_days INT NULL,
  expire_date DATE NOT NULL,
  location VARCHAR(50) NOT NULL DEFAULT '冷藏室',
  remark VARCHAR(255) NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_food_item_category (category_id),
  KEY idx_food_item_expire_date (expire_date),
  CONSTRAINT fk_food_item_category FOREIGN KEY (category_id) REFERENCES food_category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO food_category (id, name, color, sort_order) VALUES
  (1, '蔬菜', '#39b980', 10),
  (2, '水果', '#ff9f43', 20),
  (3, '肉蛋', '#f05656', 30),
  (4, '乳制品', '#5b8def', 40),
  (5, '主食', '#a071ff', 50);

INSERT INTO food_item
  (name, category_id, quantity, unit, production_date, shelf_life_days, expire_date, location, remark)
VALUES
  ('西红柿', 1, 4, '个', CURDATE(), 5, DATE_ADD(CURDATE(), INTERVAL 5 DAY), '冷藏室', '做沙拉或炒蛋'),
  ('鸡蛋', 3, 12, '枚', DATE_SUB(CURDATE(), INTERVAL 7 DAY), 30, DATE_ADD(CURDATE(), INTERVAL 23 DAY), '冷藏室', NULL),
  ('牛奶', 4, 1, '盒', DATE_SUB(CURDATE(), INTERVAL 4 DAY), 7, DATE_ADD(CURDATE(), INTERVAL 3 DAY), '冷藏室', '尽快喝完'),
  ('草莓', 2, 1, '盒', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 3, CURDATE(), '冷藏室', '今天处理');
