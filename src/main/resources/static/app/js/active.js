// // navigation
// if (document.querySelector('.navigation') != null) {
//     let depth_1_visible = document.querySelector('.depth_1>li>a');
//     let depth_1_active = document.querySelector('.depth_1_box>li.active>a');
//     let depth_1_box = document.querySelector('.depth_1_box');
//     let depth_1 = document.querySelectorAll('.depth_1_box>li');
//
//     let depth_2_visible = document.querySelector('.depth_2>li>a');
//     let depth_2_active = document.querySelector('.depth_2_box>li.active>a');
//
//     let depth_3_visible = document.querySelector('.depth_3>li>a');
//     let depth_3_active = document.querySelector('.depth_3_box>li.active>a');
//     let depth_3_control = document.querySelectorAll('.depth_3_control>a');
//
//     // active된 li 텍스트 보여줌
//     if (depth_1_visible != null && depth_1_active != null) {
//         depth_1_visible.innerText = depth_1_active.innerText;
//     }
//     if (depth_2_visible != null && depth_2_active != null) {
//         depth_2_visible.innerText = depth_2_active.innerText;
//     }
//     if (depth_3_visible != null && depth_3_active != null) {
//         depth_3_visible.innerText = depth_3_active.innerText;
//     }
//
//     let flag1 = false, flag2 = false, flag3 = false;
//
//     document.addEventListener('click', function (e) {
//
//         let depth_2_box = document.querySelector('.depth_2_box>li.active').parentElement;
//         let depth_2 = depth_2_box.querySelectorAll('li');
//
//         // if (document.querySelector('.depth_3_box>li.active') != null) {
//         //     let depth_3_box = document.querySelector('.depth_3_box>li.active').parentElement;
//         //     let depth_3 = depth_3_box.querySelectorAll('li');
//         // }
//
//         if (e.target == depth_1_visible) {
//             // 처음 1번만 실행
//             if (!flag1) {
//                 depth_1_box.style.display = 'none';
//                 flag1 = true;
//             }
//
//             // depth_1_box 열기/닫기
//             if (depth_1_box.style.display == 'none') {
//                 depth_1_box.style.display = 'block';
//             } else if (depth_1_box.style.display == 'block') {
//                 depth_1_box.style.display = 'none';
//             }
//
//             // depth_2_box, depth_3_box 닫기
//             depth_2_box.style.display = 'none';
//
//             if (document.querySelector('.depth_3_box>li.active') != null) {
//                 let depth_3_box = document.querySelector('.depth_3_box>li.active').parentElement;
//                 let depth_3 = depth_3_box.querySelectorAll('li');
//                 depth_3_box.style.display = 'none';
//             }
//         }
//
//         else if (e.target == depth_2_visible) {
//             // 처음 1번만 실행
//             if (!flag2) {
//                 let depth_2_box_all = document.querySelectorAll('.depth_2_box');
//                 depth_2_box_all.forEach(item => {
//                     item.style.display = 'none';
//                 })
//                 flag2 = true;
//             }
//
//             // depth_2_box 열기/닫기
//             if (depth_2_box.style.display == 'none') {
//                 depth_2_box.style.display = 'block';
//             } else if (depth_2_box.style.display == 'block') {
//                 depth_2_box.style.display = 'none';
//             }
//
//             // depth_1_box, depth_3_box 닫기
//             depth_1_box.style.display = 'none';
//
//             if (document.querySelector('.depth_3_box>li.active') != null) {
//                 let depth_3_box = document.querySelector('.depth_3_box>li.active').parentElement;
//                 let depth_3 = depth_3_box.querySelectorAll('li');
//                 depth_3_box.style.display = 'none';
//             }
//         }
//
//         else if (e.target == depth_3_visible) {
//             // 처음 1번만 실행
//             if (!flag3) {
//                 let depth_3_box_all = document.querySelectorAll('.depth_3_box');
//                 depth_3_box_all.forEach(item => {
//                     item.style.display = 'none';
//                 })
//                 flag3 = true;
//             }
//
//             // depth_2_box 열기/닫기
//             if (document.querySelector('.depth_3_box>li.active') != null) {
//                 let depth_3_box = document.querySelector('.depth_3_box>li.active').parentElement;
//                 let depth_3 = depth_3_box.querySelectorAll('li');
//
//                 if (depth_3_box.style.display == 'none') {
//                     depth_3_box.style.display = 'block';
//                 } else if (depth_3_box.style.display == 'block') {
//                     depth_3_box.style.display = 'none';
//                 }
//             }
//
//             // depth_1_box, depth_2_box 닫기
//             depth_1_box.style.display = 'none';
//             depth_2_box.style.display = 'none';
//         }
//
//         else {
//             // depth_1_box, depth_2_box, depth_3_box 닫기
//             depth_1_box.style.display = 'none';
//             depth_2_box.style.display = 'none';
//             if (document.querySelector('.depth_3_box>li.active') != null) {
//                 let depth_3_box = document.querySelector('.depth_3_box>li.active').parentElement;
//                 let depth_3 = depth_3_box.querySelectorAll('li');
//                 depth_3_box.style.display = 'none';
//             }
//         }
//
//         depth_1.forEach((item, idx) => {
//             if (e.target == item.children[0]) {
//                 if (!item.classList.contains('active')) { // 클릭한 li가 active 되어있지 않은 경우
//                     // 전체 active 삭제, 클릭한 li active 추가
//                     depth_1.forEach(li => {
//                         li.classList.remove('active');
//                     });
//                     item.classList.add('active');
//
//                     // 클릭한(active) li 텍스트 보여줌
//                     depth_1_visible.innerText = item.children[0].innerText;
//
//                     // depth_2_box 전체 li의 acitve 삭제
//                     let depth_2 = document.querySelectorAll('.depth_2_box>li');
//                     for (let i = 0; i < depth_2.length; i++) {
//                         depth_2[i].classList.remove('active');
//                     }
//
//                     // active된 li 번째 depth_2_box의 가장 첫번째 자식에 active 추가
//                     let depth_2_box = document.querySelectorAll('.depth_2_box');
//                     depth_2_box[idx].querySelector('li:first-child').classList.add('active');
//                     // active 추가된 노드가 depth_3_control 클레스를 가지는 경우
//                     if (depth_2_box[idx].querySelector('li:first-child').classList.contains('depth_3_control')) {
//                         // depth_3 영역 보여줌
//                         depth_3_visible.style.display = 'block';
//
//                         // depth_3_box 전체 li의 acitve 삭제
//                         let depth_3 = document.querySelectorAll('.depth_3_box>li');
//                         for (let i = 0; i < depth_3.length; i++) {
//                             depth_3[i].classList.remove('active');
//                         }
//
//                         // depth 2 > depth_3_control 클레스를 가진 li 중 active된 li 번째 depth_3_box의 가장 첫번째 자식에 active 추가
//                         let depth_3_box = document.querySelectorAll('.depth_3_box');
//                         let depth_3_control = document.querySelectorAll('.depth_3_control');
//                         // let control_idx;
//                         depth_3_control.forEach((control, control_idx) => {
//                             if (control == depth_2_box[idx].querySelector('li:first-child')) {
//                                 depth_3_box[control_idx].querySelector('li:first-child').classList.add('active');
//                             }
//                         })
//
//                         // depth_3_visible 영역에 active된 텍스트 보여줌
//                         depth_3_visible.innerText = document.querySelector('.depth_3_box>li.active>a').innerText;
//                     } else {
//                         // depth_3 영역 숨김
//                         depth_3_visible.style.display = 'none';
//                     }
//
//                     // depth_2_visible 영역에 active된 텍스트 보여줌
//                     depth_2_visible.innerText = document.querySelector('.depth_2_box>li.active>a').innerText;
//                 }
//             }
//         });
//
//         depth_2.forEach((item, idx) => {
//             if (e.target == item.children[0]) {
//                 if (!item.classList.contains('active')) { // 클릭한 li가 active 되어있지 않은 경우
//                     // 전체 active 삭제, 클릭한 li active 추가
//                     depth_2.forEach(li => {
//                         li.classList.remove('active');
//                     });
//                     item.classList.add('active');
//
//                     // 클릭한(active) li 텍스트 보여줌
//                     depth_2_visible.innerText = item.children[0].innerText;
//
//                     // 클릭한 li가 depth_3_control 클레스를 가질 경우
//                     if (item.classList.contains('depth_3_control')) {
//                         // depth_3 영역 보여줌
//                         depth_3_visible.style.display = 'block';
//                     } else {
//                         // depth_3 영역 숨김
//                         depth_3_visible.style.display = 'none';
//                     }
//                 }
//             }
//         });
//
//         let depth_3_control = document.querySelectorAll('.depth_3_control');
//         depth_3_control.forEach((item, idx) => {
//             if (e.target == item.children[0]) {
//                 // depth_3_box 전체 li의 acitve 삭제
//                 let depth_3 = document.querySelectorAll('.depth_3_box>li');
//                 for (let i = 0; i < depth_3.length; i++) {
//                     depth_3[i].classList.remove('active');
//                 }
//
//                 // active된 li 번째 depth_3_box의 가장 첫번째 자식에 active 추가
//                 let depth_3_box = document.querySelectorAll('.depth_3_box');
//                 depth_3_box[idx].querySelector('li:first-child').classList.add('active');
//
//                 // depth_3_visible 영역에 active된 텍스트 보여줌
//                 depth_3_visible.innerText = document.querySelector('.depth_3_box>li.active>a').innerText;
//             }
//         });
//
//         if (document.querySelector('.depth_3_box>li.active') != null) {
//             let depth_3_box = document.querySelector('.depth_3_box>li.active').parentElement;
//             let depth_3 = depth_3_box.querySelectorAll('li');
//             depth_3.forEach((item, idx) => {
//                 if (e.target == item.children[0]) {
//                     if (!item.classList.contains('active')) { // 클릭한 li가 active 되어있지 않은 경우
//                         // 전체 active 삭제, 클릭한 li active 추가
//                         depth_3.forEach(li => {
//                             li.classList.remove('active');
//                         });
//                         item.classList.add('active');
//
//                         // 클릭한(active) li 텍스트 보여줌
//                         depth_3_visible.innerText = item.children[0].innerText;
//                     }
//                 }
//             });
//         }
//
//     });
// }