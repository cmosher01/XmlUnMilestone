# XML Un-milestone

Java library to change XML leading "milestone" elements into normal elements.

```xml
<x>
  AB
  <ms/>
  CD
  <ms/>
  EF
</x>
```

becomes


```xml
<x>
  AB
  <ms>
    CD
  </ms>
  <ms>
    EF
  </ms>
</x>
```

where `ms` is the milestone tag.

Milestone elements are presumed empty.
