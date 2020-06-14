# VotingQ Backend API

### Endpoints

#### `GET /centers`

Returns voting centers with available queues matching election ID and within 
address' location radius. Includes current active queue, address, name, id, and polling hours.

(Response currently hardcoded.)

##### Query Parameters

- `electionId`:  `number`*,
- `street1`: `string`*,
- `street2`: `string,
- `city`: `string`*,
- `state`: `string`*,
- `zip`: `string`
    
(*) Required

Todo: 
- Have a dynamic response, not a hardcoded one. :)

##### Sample (Hardcoded) Response

```json
[
    {
        "id": 1,
        "name": "Chapel Hills Mall",
        "address": {
            "street1": "1910 Briargate Boulevard",
            "city": "Colorado Springs",
            "state": "CO",
            "zip": "80920"
        },
        "pollingHours": {
            "start": "2020-06-14T08:00:00-07:00",
            "finish": "2020-06-14T19:00:00-07:00"
        },
        "currentQueue": {
            "id": 1,
            "start": "2020-06-14T08:00:00-07:00",
            "finish": "2020-06-14T19:00:00-07:00",
            "capacity": 1,
            "size": 0
        }
    },
    {
        "id": 2,
        "name": "Vista Grande Baptist Church",
        "address": {
            "street1": "5680 Stetson Hills Boulevard",
            "city": "Colorado Springs",
            "state": "CO",
            "zip": "80917"
        },
        "pollingHours": {
            "start": "2020-06-14T08:00:00-07:00",
            "finish": "2020-06-14T19:00:00-07:00"
        },
        "currentQueue": {
            "id": 1,
            "start": "2020-06-14T08:00:00-07:00",
            "finish": "2020-06-14T19:00:00-07:00",
            "capacity": 1,
            "size": 0
        }
    },
    {
        "id": 3,
        "name": "EPC Clerk's Office North Branch",
        "address": {
            "street1": "8830 North Union Boulevard",
            "street2": "Ste 505",
            "city": "Colorado Springs",
            "state": "CO",
            "zip": "80920"
        },
        "pollingHours": {
            "start": "2020-06-14T08:00:00-07:00",
            "finish": "2020-06-14T19:00:00-07:00"
        },
        "currentQueue": {
            "id": 1,
            "start": "2020-06-14T08:00:00-07:00",
            "finish": "2020-06-14T19:00:00-07:00",
            "capacity": 1,
            "size": 0
        }
    }
]
```

### `GET /center`

Returns a single voting center's detailed information. In addition to `/centers` information of 
id, name, address, active queue, and polling hours, returns voter requirements and current user's 
position in queue, if already in the queue.

#### Query Parameters

- `centerId`: `number`*
- `voterId`: `number`

(*) Required.

Todo: 
- Get `voterId` from session, not as a query parameter.
- Make `centerId` a path parameter, not a query parameter.
