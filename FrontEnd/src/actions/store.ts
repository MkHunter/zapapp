import { createStore, applyMiddleware, compose } from "redux";
import ReduxThunk from "redux-thunk";
import reducers from "../reducers";

import { persistStore } from 'redux-persist';

const store = createStore( //Export
    reducers,
    compose(
        applyMiddleware(ReduxThunk)
    )
);

const persistor = persistStore(store); //Export

export { store, persistor };