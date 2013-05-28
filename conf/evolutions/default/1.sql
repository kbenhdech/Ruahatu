# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "ATLAS_FISH" ("ATLAS_FISH_ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"SCIENTIFIC_NAME" VARCHAR NOT NULL,"COMMON_NAME" VARCHAR NOT NULL);
create unique index "ATLAS_FISH_INDEX_NAME" on "ATLAS_FISH" ("SCIENTIFIC_NAME");

# --- !Downs

drop table "ATLAS_FISH";
