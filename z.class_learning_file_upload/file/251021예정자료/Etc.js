

import React from 'react';

import CommonTop from '../../commonComponet/CommonTop';
import Etc01 from './Etc01';

import 'bootstrap/dist/css/bootstrap.min.css';
import { Col, Container, Row } from 'react-bootstrap';


const Etc = () => {

console.log("Etc");

return (

<Container>
{/*
- https://react-bootstrap.github.io/
- layout/grid/ 참조
<Container fluid={true}>
- fluid={true} 속성은 화면 크기에 맞춰서 자동으로 크기를 조정함
- Fluid 의 의미 / default 는 ?
*/}
<CommonTop/>

<Row>
<Col lg={3}><Etc01/></Col>
<Col lg={9}><Etc01/></Col>
</Row>

<Etc01/>

</Container>
);
};

export default Etc;