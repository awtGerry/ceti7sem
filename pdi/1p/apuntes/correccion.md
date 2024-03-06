# Collecta programa dentro de interpru* en matlab
```m
    funtion union = colecta(u)
        num = 1:256
        vai = 288 (u*3000)
        union (:,1) = num
        union (:,2) = vai
    end

    % acumulado
    function data = histoacu(u)
    num = 1:256;
    acc = 0;
    histoAc = zeros(1,256);
        for x=1:256
            histoAc(x) = acc+u(x);
            acc = histoAc(x);
        end
        val = 210-(histoAc*200);
        data(:,1) = num;
        data(:,2) = val;
    end
```
