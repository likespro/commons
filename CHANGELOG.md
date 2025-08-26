### New features
`core` module:
- Add `Pagination` class to work with pagination
- Add `Repository` interface with common repository methods
- Add `Timestamp` value class for representing timestamps
- Add `Latitude` and `Longitude` value classes for representing geographic coordinates
- Add `PlaceId` value class for representing place identifiers on Google Maps and Apple Maps
- Add `CustomGeolocation` value class for representing custom geographic locations
`core-mit` module
- Add `DetailsConfiguration` for `WrappedException.kt` and `EncodableResult.kt` to customize exception details inclusion
- Add `randomBase64()` method to generate random Base64 of the specified length
- Add `Value` interface to represent a value in wrappers
- Add `Entity` interface to represent an entity
- Add `Aggregate` interface to represent an aggregation
- Add `Validatable` interface to represent a validatable object
`reflection` module:
- `decodeObject()` now auto-validates the object if it implements `Validatable`
### Fixed Bugs
`core-mit` module:
- Wrapping of `WrappedException` now returns the original `WrappedException`
`reflection` module:
- `.encodeObject()`/`.decodeObject()` not working properly with `null`
- `.decodeObject()` not working properly with Java `Void`
- `.boxed()`/`.unboxed()` not working properly with Java `Void`