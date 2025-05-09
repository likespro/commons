## Added Object Encoder
Now you can encode and decode objects to and from String
using `object.encodeObject()` and `String.decodeObject()` respectively.
This is useful for storing objects in a database or sending them over the network.
## JSON Conversions improvements
`toJSONArray()` now works on all arrays. However, programmer should manually think if object is convertable to `JSONArray` or to `JSONObject`.