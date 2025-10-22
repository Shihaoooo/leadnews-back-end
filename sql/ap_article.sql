create database leadnews_article;

use leadnews_article;

CREATE TABLE ap_article (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                              `title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
                              `author_id` int(11) unsigned DEFAULT NULL COMMENT '文章作者的ID',
                              `author_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者昵称',
                              `channel_id` int(10) unsigned DEFAULT NULL COMMENT '文章所属频道ID',
                              `channel_name` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '频道名称',
                              `layout` tinyint(3) unsigned DEFAULT NULL COMMENT '文章布局\n        0 无图文章\n        1 单图文章\n        2 多图文章',
                              `flag` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文章标记\n        0 普通文章\n        1 热点文章\n        2 置顶文章\n        3 精品文章\n        4 大V文章',
                              `labels` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文章标签最多3个 逗号分隔',
                              `like` int(5) unsigned DEFAULT NULL COMMENT '点赞数量',
                              `collection` int(5) unsigned DEFAULT NULL COMMENT '收藏数量',
                              `comment` int(5) unsigned DEFAULT NULL COMMENT '评论数量',
                              `read_count` int(5) unsigned DEFAULT NULL COMMENT '阅读数量',
                              `province_id` int(11) unsigned DEFAULT NULL COMMENT '省市',
                              `city_id` int(11) unsigned DEFAULT NULL COMMENT '市区',
                              `county_id` int(11) unsigned DEFAULT NULL COMMENT '区县',
                              `created_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
                              `sync_status` tinyint(1) DEFAULT '0' COMMENT '同步状态',
                              `origin` tinyint(1) DEFAULT '0' COMMENT '来源',
                              `static_url` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1383828014629179394 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='文章信息表，存储已发布的文章';

create table ap_article_content
(
    id         bigint(20) unsigned not null AUTO_INCREMENT comment '主键',
    article_id bigint(20) unsigned DEFAULT NULL comment '文章id',
    content    longtext collate utf8mb4_unicode_ci comment '文章内容',
    primary key (id) using btree,
    KEY idx_article_id (article_id) using btree
) ENGINE = InnoDB AUTO_INCREMENT=1383828014650150915 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC comment 'APP已发布文章内容表';

CREATE TABLE `ap_article_config` (
                                     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `article_id` bigint(20) unsigned DEFAULT NULL COMMENT '文章ID',
                                     `is_comment` tinyint(1) unsigned DEFAULT NULL COMMENT '是否可评论',
                                     `is_forward` tinyint(1) unsigned DEFAULT NULL COMMENT '是否转发',
                                     `is_down` tinyint(1) unsigned DEFAULT NULL COMMENT '是否下架',
                                     `is_delete` tinyint(1) unsigned DEFAULT NULL COMMENT '是否已删除',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     KEY `idx_article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1383828014645956610 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='APP发布文章配置表';

INSERT INTO `ap_article` (
    `title`, `author_id`, `author_name`, `channel_id`, `channel_name`,
    `layout`, `flag`, `labels`, `like`, `collection`, `comment`,
    `read_count`, `province_id`, `city_id`, `county_id`, `created_time`,
    `publish_time`, `sync_status`, `origin`, `static_url`
) VALUES
-- 第1条
('AI技术如何改变未来生活', 1001, '科技达人', 1, '科技', 1, '1,3', 'AI,科技,未来', 235, 48, 12, 1568, 110000, 110100, 110101, '2025-01-15 08:30:00', '2025-01-15 09:00:00', 1, 0, 'https://static.example.com/ai-future.html'),
-- 第2条
('家常红烧肉的3个秘诀', 1002, '美食家阿明', 2, '生活', 2, '0', '美食,家常菜,烹饪', 562, 132, 45, 3240, 310000, 310100, 310104, '2025-02-20 11:15:00', '2025-02-20 12:00:00', 1, 0, 'https://static.example.com/pork-recipe.html'),
-- 第3条
('小学生高效学习方法指南', 1003, '教育专家李老师', 3, '教育', 0, '2,3', '教育,学习,小学', 890, 256, 89, 5672, 440000, 440300, 440305, '2025-03-05 14:20:00', '2025-03-05 15:00:00', 1, 1, 'https://static.example.com/study-method.html'),
-- 第4条
('2025年最值得入手的5款手机', 1004, '数码测评师', 1, '科技', 1, '1', '数码,手机,测评', 342, 98, 23, 2156, 120000, 120100, 120103, '2025-04-10 09:45:00', '2025-04-10 10:30:00', 0, 0, 'https://static.example.com/phone-2025.html'),
-- 第5条
('春季旅游必去的8个小众景点', 1005, '旅行博主小雅', 4, '旅游', 2, '0,4', '旅游,春季,小众', 678, 189, 56, 4321, 330000, 330100, 330106, '2025-05-01 07:30:00', '2025-05-01 08:00:00', 1, 0, 'https://static.example.com/spring-travel.html'),
-- 第6条
('篮球运球进阶技巧教学', 1006, '体育教练老王', 5, '体育', 1, '0', '体育,篮球,技巧', 210, 56, 18, 1289, 410000, 410100, 410102, '2025-06-15 16:00:00', '2025-06-15 16:30:00', 1, 0, 'https://static.example.com/basketball-dribble.html'),
-- 第7条
('职场新人必备的沟通技巧', 1007, '职场导师Lisa', 6, '职场', 0, '3', '职场,沟通,新人', 456, 123, 34, 2890, 320000, 320100, 320104, '2025-07-20 10:10:00', '2025-07-20 11:00:00', 0, 1, 'https://static.example.com/communication-skill.html'),
-- 第8条
('经典电影《肖申克的救赎》深度解析', 1008, '影评人阿哲', 7, '娱乐', 1, '1,3', '电影,经典,解析', 789, 210, 98, 6543, 510000, 510100, 510104, '2025-08-05 19:30:00', '2025-08-05 20:00:00', 1, 0, 'https://static.example.com/shawshank.html'),
-- 第9条
('阳台种菜全攻略：从播种到收获', 1009, '园艺爱好者小绿', 2, '生活', 2, '0', '园艺,生活,种菜', 321, 87, 26, 1987, 500000, 500100, 500102, '2025-09-10 14:00:00', '2025-09-10 15:00:00', 1, 0, 'https://static.example.com/balcony-gardening.html'),
-- 第10条
('Python入门到精通：30天学习计划', 1010, '编程老师张', 1, '科技', 0, '2,4', '编程,Python,学习', 908, 345, 120, 8765, 110000, 110100, 110105, '2025-10-01 09:00:00', '2025-10-01 10:00:00', 1, 1, 'https://static.example.com/python-30days.html');

INSERT INTO `ap_article_content` (`article_id`, `content`) VALUES
-- 对应ap_article第1条（id=1383828014629179394）
(1383828014629179394, '本文将从医疗、教育、交通三个领域，详细介绍AI技术如何改变未来生活。在医疗方面，AI辅助诊断系统已能精准识别早期癌症；教育领域，个性化学习机器人可根据学生进度调整课程...'),
-- 对应ap_article第2条（id=1383828014629179395）
(1383828014629179395, '红烧肉是中国传统名菜，想要做得肥而不腻、入口即化，关键在这三点：1. 五花肉选三层肥瘦相间的；2. 焯水时加姜片和料酒去腥味；3. 冰糖炒色要小火慢炒...'),
-- 对应ap_article第3条（id=1383828014629179396）
(1383828014629179396, '小学生高效学习的核心是培养习惯：1. 制定每日计划表，明确学习和休息时间；2. 采用番茄工作法，25分钟专注+5分钟休息；3. 睡前30分钟复习当天内容...'),
-- 对应ap_article第4条（id=1383828014629179397）
(1383828014629179397, '2025年手机市场竞争激烈，这5款机型脱颖而出：1. 品牌A Pro：搭载最新芯片，续航超12小时；2. 品牌B Ultra：一亿像素相机，夜景拍摄无敌...'),
-- 对应ap_article第5条（id=1383828014629179398）
(1383828014629179398, '春季旅游不想人挤人？这8个小众景点值得一去：1. 云南丙察察：原始村落与雪山美景；2. 浙江楠溪江：竹筏漂流+古村落...'),
-- 对应ap_article第6条（id=1383828014629179399）
(1383828014629179399, '篮球运球进阶技巧：1. 交叉运球：重心压低，快速切换左右手；2. 背后运球：手腕发力，球贴身体旋转；3. 胯下运球：配合脚步移动...'),
-- 对应ap_article第7条（id=1383828014629179400）
(1383828014629179400, '职场新人沟通要注意：1. 汇报工作说结果，而非过程；2. 请教问题前先自己查资料；3. 拒绝他人时要给出替代方案...'),
-- 对应ap_article第8条（id=1383828014629179401）
(1383828014629179401, '《肖申克的救赎》中，安迪用20年时间挖通隧道，不仅是物理上的逃离，更是对“希望”的诠释。影片中“要么忙着活，要么忙着死”这句台词，道尽了生命的意义...'),
-- 对应ap_article第9条（id=1383828014629179402）
(1383828014629179402, '阳台种菜步骤：1. 选盆：深度30cm以上，底部有排水孔；2. 选种：新手推荐生菜、小葱、樱桃番茄；3. 施肥：每周一次有机肥...'),
-- 对应ap_article第10条（id=1383828014629179403）
(1383828014629179403, '30天Python学习计划：第1-7天：基础语法（变量、循环、条件）；第8-15天：函数与模块；第16-23天：文件操作与数据库；第24-30天：实战项目（爬虫+数据分析）...');

INSERT INTO `ap_article_content` (`article_id`, `content`) VALUES
-- 对应ap_article第1条（id=1383828014629179394）
(1383828014629179394, '本文将从医疗、教育、交通三个领域，详细介绍AI技术如何改变未来生活。在医疗方面，AI辅助诊断系统已能精准识别早期癌症；教育领域，个性化学习机器人可根据学生进度调整课程...'),
-- 对应ap_article第2条（id=1383828014629179395）
(1383828014629179395, '红烧肉是中国传统名菜，想要做得肥而不腻、入口即化，关键在这三点：1. 五花肉选三层肥瘦相间的；2. 焯水时加姜片和料酒去腥味；3. 冰糖炒色要小火慢炒...'),
-- 对应ap_article第3条（id=1383828014629179396）
(1383828014629179396, '小学生高效学习的核心是培养习惯：1. 制定每日计划表，明确学习和休息时间；2. 采用番茄工作法，25分钟专注+5分钟休息；3. 睡前30分钟复习当天内容...'),
-- 对应ap_article第4条（id=1383828014629179397）
(1383828014629179397, '2025年手机市场竞争激烈，这5款机型脱颖而出：1. 品牌A Pro：搭载最新芯片，续航超12小时；2. 品牌B Ultra：一亿像素相机，夜景拍摄无敌...'),
-- 对应ap_article第5条（id=1383828014629179398）
(1383828014629179398, '春季旅游不想人挤人？这8个小众景点值得一去：1. 云南丙察察：原始村落与雪山美景；2. 浙江楠溪江：竹筏漂流+古村落...'),
-- 对应ap_article第6条（id=1383828014629179399）
(1383828014629179399, '篮球运球进阶技巧：1. 交叉运球：重心压低，快速切换左右手；2. 背后运球：手腕发力，球贴身体旋转；3. 胯下运球：配合脚步移动...'),
-- 对应ap_article第7条（id=1383828014629179400）
(1383828014629179400, '职场新人沟通要注意：1. 汇报工作说结果，而非过程；2. 请教问题前先自己查资料；3. 拒绝他人时要给出替代方案...'),
-- 对应ap_article第8条（id=1383828014629179401）
(1383828014629179401, '《肖申克的救赎》中，安迪用20年时间挖通隧道，不仅是物理上的逃离，更是对“希望”的诠释。影片中“要么忙着活，要么忙着死”这句台词，道尽了生命的意义...'),
-- 对应ap_article第9条（id=1383828014629179402）
(1383828014629179402, '阳台种菜步骤：1. 选盆：深度30cm以上，底部有排水孔；2. 选种：新手推荐生菜、小葱、樱桃番茄；3. 施肥：每周一次有机肥...'),
-- 对应ap_article第10条（id=1383828014629179403）
(1383828014629179403, '30天Python学习计划：第1-7天：基础语法（变量、循环、条件）；第8-15天：函数与模块；第16-23天：文件操作与数据库；第24-30天：实战项目（爬虫+数据分析）...');

INSERT INTO `ap_article_config` (`article_id`, `is_comment`, `is_forward`, `is_down`, `is_delete`) VALUES
-- 对应ap_article第1条
(1383828014629179394, 1, 1, 0, 0),
-- 对应ap_article第2条
(1383828014629179395, 1, 1, 0, 0),
-- 对应ap_article第3条
(1383828014629179396, 1, 0, 0, 0),  -- 禁止转发
-- 对应ap_article第4条
(1383828014629179397, 0, 1, 0, 0),  -- 禁止评论
-- 对应ap_article第5条
(1383828014629179398, 1, 1, 0, 0),
-- 对应ap_article第6条
(1383828014629179399, 1, 1, 1, 0),  -- 已下架
-- 对应ap_article第7条
(1383828014629179400, 1, 1, 0, 0),
-- 对应ap_article第8条
(1383828014629179401, 1, 1, 0, 1),  -- 已删除
-- 对应ap_article第9条
(1383828014629179402, 0, 0, 0, 0),  -- 禁止评论和转发
-- 对应ap_article第10条
(1383828014629179403, 1, 1, 0, 0);
/*test*/

## 按照发布时间倒序查询10条文章

## 频道筛选

## 加载首页


## 加载更多


## 加载最新
select * from ap_article;

select * from ap_article aa left join ap_article_config aac on aa.id = aac.article_id
where aac.is_delete != 1
  and aac.is_down != 1
  and aa.channel_id = 1
  and aa.publish_time < '2029-09-07 22:30:09'
order by aa.publish_time DESC
limit 10;