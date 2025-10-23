

import './App.css';

import { Route, Switch } from 'react-router-dom';
import Main from './02mainComponents/Main';
import User from './03userComponents/User';
import CommonTop from './commonComponet/CommonTop';
import NotFound from './commonComponet/NotFound';
import Product from './04productComponents/Product';


 function App() {
 console.log("App");

 return (
 
 <div className="ViewGood">
 
 {/* 모든 Page 상단에 동일한 화면을 구성하기 위해서 공통 컴포넌트로 분리함 */}
 <CommonTop/>

 <Switch>
 <Route path='/' exact={true} ><Main/></Route>
 <Route path='/user'><User/></Route>
 <Route path='/product'><Product/></Route>
 <Route path='*'><NotFound/></Route>
 </Switch>

 </div>
 );
 }

 export default App;