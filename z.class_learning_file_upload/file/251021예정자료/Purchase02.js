

import { Link } from 'react-router-dom';

const Purchase02 = () => {

console.log("Purchase02")

return (
<div className="ViewGood">

<h2>Purchase02</h2>
Product02...
<br/><br/>

<Link to='/'>Main</Link> &nbsp; &nbsp;
<Link to='/purchase/purchase01'>Purchase01:URL parameters 값 없을때...</Link>
&nbsp; &nbsp; &nbsp; &nbsp;

{/* - URL Parameters 값 전달.

>> 라우터 설정
<Route
path='/purchase/purchase01/:purchaseName/:price'><Purchase01/></Route>

>> 정상적인 경우 : <Link to='/purchase/purchase01/TV/1000'>
의미 : /purchase/Purchase01 이동시 TV 와 price 전달
*/}

<Link to='/purchase/purchase03'>Purchase03:URL QueryString 값 없을때...</Link>

{/* - QueryString 값 전달.

>> 라우터 설정
<Route path='/purchase/purchase03'><Purchase03/></Route>

>> 정상적인 경우 : <Link to='/purchase/purchase03?purchaseName=TV&price=1000'>
의미 : /purchase/purchase03 이동시 TV 와 price 전달.
*/}

</div>
);
}

export default Purchase02;