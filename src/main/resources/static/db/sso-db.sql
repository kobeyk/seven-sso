DROP TABLE IF EXISTS "public"."t_sso_user";
CREATE TABLE "public"."t_sso_user" (
  "uid" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "info" text COLLATE "pg_catalog"."default",
  "alias" varchar(255) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "ctime" timestamp(6),
  "utime" timestamp(6)
)
;
ALTER TABLE "public"."t_sso_user" ADD CONSTRAINT "t_sso_user_pkey" PRIMARY KEY ("uid");
