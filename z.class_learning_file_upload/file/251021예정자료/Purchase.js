

import { Link, Route, Switch, useRouteMatch } from 'react-router-dom';
import Purchase01 from './Purchase01';
import Purchase02 from './Purchase02';
import Purchase03 from './Purchase03';
import NotFound from './commonCompone/NotFound';

const Purchase = () => {

console.log("Purchase");

// www.reactrouter.com : Nesting 참조.
// 1. path, url 값 확인
// Destructuring Assignment : 비구조화 할당 / 구조 분해 할당
// useRouteMatch() : 현재 Route의 path 와 url 을 확인할 수 있다.
let { path, url } = useRouteMatch();
console.log(path);
console.log(url);

return (

<div className="ViewGood">

{/* - URL Parameters 값 전달.
>> URL 을 이용 컴포넌트 전달 해야 경우.
>> http://ip:port/보여줄Component/data/data/data

www.reactrouter.com : URL parameter 참조

>> <Link to='/purchase/purchase01/TV/1000'>
의미 : /purchase/purchase01 이동시 TV 와 price 전달
*/}

<Link to={url+'/purchase01/TV/1000'}>Purchase01</Link> &nbsp; &nbsp; &nbsp; &nbsp;
<Link to={url+'/purchase02'}>Purchase02</Link> &nbsp; &nbsp; &nbsp; &nbsp;
<Link to='/purchase/purchase03?purchaseName=TV&price=1000'>Purchase03</Link> &nbsp; &nbsp; &nbsp; &nbsp;

{/* - QueryString 값 전달.
>> URL을 이용 컴포넌트 전달 해야 경우.
>> http://ip:port/보여줄Component?name=value&name=value

www.reactrouter.com : Query parameters 참조

>> <Link to='/purchase/purchase03?purchaseName=TV&price=1000'>
의미 : /purchase/purchase03 이동시 TV 와 price 전달.
*/}

<Switch>
<Route
path={path+'/purchase01/:purchaseName/:price'}><Purchase01/></Route>
<Route path={path+'/purchase02'}><Purchase02/></Route>
<Route path={path+'/purchase03'}><Purchase03/></Route>
<Route path={path+'/*'}><NotFound/></Route>
</Switch>

</div>
);
};

export default Purchase;