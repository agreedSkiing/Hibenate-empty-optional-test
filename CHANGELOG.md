# Changelog

## Commit 168246d44dce12fc3c401027df79fb15d238df23

Took this refactoring a bit further and also updated all the test with a working Hibernate ORM 6 query that works across all implementations. I needed to define a new parameter for single check `:parameterPresent = FALSE` and list check queries making the solution from https://www.baeldung.com/spring-data-jpa-null-parameters not applicable in Hibernate ORM 6 and the same for https://stackoverflow.com/a/66956214 answer which was the test for for all previous commits so i went with https://stackoverflow.com/a/67179440 suggestion instead, but the https://stackoverflow.com/a/2489053 answer with ceritera queries is probably a better approach for this kind of solution (the current working one feels like a workaround for this).

## Commit ee6d3c3737b126b3464ae0913d9e564215133981

So yeah... the single check queries from baeldung do not work at all here with UUID being null, so i think the only help would be to use the same approach as with `IN :parameter` queries, adding another value to the query ☹. See ee6d3c3737b126b3464ae0913d9e564215133981-failures.md

## Commit 8350d6c3a16b78cd480445cc4dfdcd1011faa5fa

Rename done to new group id and fixed formatting in file from d303fc144a1c10325364f05e11a9661880a94bc5-failures.md

## Commit d303fc144a1c10325364f05e11a9661880a94bc5

Hibernate 5 doing everything awesome again and this is where I tried to just pass in an empty list or null list into a standard JPQL/HQL query and noticed that the `COALESCE(:list) IS NOT NULL` is not needed for optional queries with list, this also caused a rename of the whole project to `<groupId>se.agreedskiing.hibernate.optional</groupId>` in the commits coming after this one. Still Hibernate Reactive or in this case Mutiny can't map UUID and ID to a list query in case of the query `SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.uu IN (:list))` 🤯, and Hibernate ORM 6 still has problems with an empty list in the optional query.

Next I will try to do optional queries with single item like link from baeldung and start combiding list queries with `AND`.

For the failures check [the commit markdown file](test-failures/d303fc144a1c10325364f05e11a9661880a94bc5-failures.md)

## Commit cc63cdeb5038a41f2e1b673cc22834cefe7d6bc8

This commit works great with Hibernate 5 and replaced immutable list and empty collections.empty with arraylists.

But Hibernate ORM 6 still produces som errors but less.

For the failures check [the commit markdown file](test-failures/cc63cdeb5038a41f2e1b673cc22834cefe7d6bc8-failures.md).

## Commit 00e1cc72480f6de27ccccf62417108c36cd17772

This commit works great with Hibernate 5 but some errors are produced in Hibernate Reactive and Hibernate ORM 6 when doing trying to do the `SELECT t FROM Test t WHERE (COALESCE(:list) IS NOT NULL OR t.id IN (:list))`, `t.uu` or `t.field` where id is a Long, uu is an UUID and field is a String.

So many errors from Hibernate 6 ORM, but wait it does not seem to like Inmutable lists and Collections.empty 😮.

For the failures check [the commit markdown file](test-failures/00e1cc72480f6de27ccccf62417108c36cd17772-failures.md).
